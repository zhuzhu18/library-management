<template>
  <div>
    <el-card style="margin-bottom: 20px;">
      <template #header>
        <span style="font-size: 18px; font-weight: bold;">借书</span>
      </template>
      <el-form :inline="true" :model="borrowForm">
        <el-form-item label="图书">
          <el-select v-model="borrowForm.bookId" placeholder="选择图书" filterable style="width: 240px">
            <el-option v-for="b in books" :key="b.id" :label="`${b.title} (库存: ${b.stock})`" :value="b.id" :disabled="b.stock <= 0" />
          </el-select>
        </el-form-item>
        <el-form-item label="读者">
          <el-select v-model="borrowForm.readerId" placeholder="选择读者" filterable style="width: 200px">
            <el-option v-for="r in readers" :key="r.id" :label="r.name" :value="r.id" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleBorrow">借书</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card>
      <template #header>
        <span style="font-size: 18px; font-weight: bold;">借阅记录</span>
      </template>

      <el-table :data="records" stripe style="width: 100%">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column label="图书" width="200">
          <template #default="{ row }">{{ row.book?.title }}</template>
        </el-table-column>
        <el-table-column label="读者" width="120">
          <template #default="{ row }">{{ row.reader?.name }}</template>
        </el-table-column>
        <el-table-column prop="borrowDate" label="借书日期" width="140" />
        <el-table-column label="还书日期" width="140">
          <template #default="{ row }">
            <span v-if="row.returnDate">{{ row.returnDate }}</span>
            <el-tag v-else type="warning" size="small">未归还</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.returnDate ? 'success' : 'danger'" size="small">
              {{ row.returnDate ? '已归还' : '借出中' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-button v-if="!row.returnDate" size="small" type="success" @click="handleReturn(row)">还书</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { borrowApi, bookApi, readerApi } from '../api'

const records = ref([])
const books = ref([])
const readers = ref([])
const borrowForm = ref({ bookId: null, readerId: null })

const loadData = async () => {
  const [recordRes, bookRes, readerRes] = await Promise.all([
    borrowApi.list(),
    bookApi.list(),
    readerApi.list()
  ])
  records.value = recordRes.data
  books.value = bookRes.data
  readers.value = readerRes.data
}

const handleBorrow = async () => {
  if (!borrowForm.value.bookId || !borrowForm.value.readerId) {
    ElMessage.warning('请选择图书和读者')
    return
  }
  await borrowApi.borrow(borrowForm.value)
  ElMessage.success('借书成功')
  borrowForm.value = { bookId: null, readerId: null }
  loadData()
}

const handleReturn = async (row) => {
  await borrowApi.returnBook(row.id)
  ElMessage.success('还书成功')
  loadData()
}

onMounted(loadData)
</script>
