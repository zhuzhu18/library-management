<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span style="font-size: 18px; font-weight: bold;">图书管理</span>
          <div>
            <el-input v-model="searchText" placeholder="搜索书名或作者" style="width: 200px; margin-right: 10px;" clearable @clear="loadBooks" @keyup.enter="handleSearch" />
            <el-button type="primary" @click="handleSearch">搜索</el-button>
            <el-button type="success" @click="handleAdd">新增图书</el-button>
          </div>
        </div>
      </template>

      <el-table :data="books" stripe style="width: 100%">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="isbn" label="ISBN" width="160" />
        <el-table-column prop="title" label="书名" />
        <el-table-column prop="author" label="作者" width="150" />
        <el-table-column prop="publisher" label="出版社" width="180" />
        <el-table-column prop="stock" label="库存" width="80" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑图书' : '新增图书'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="ISBN">
          <el-input v-model="form.isbn" />
        </el-form-item>
        <el-form-item label="书名">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="form.author" />
        </el-form-item>
        <el-form-item label="出版社">
          <el-input v-model="form.publisher" />
        </el-form-item>
        <el-form-item label="库存">
          <el-input-number v-model="form.stock" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { bookApi } from '../api'

const books = ref([])
const searchText = ref('')
const dialogVisible = ref(false)
const isEdit = ref(false)
const form = ref({ isbn: '', title: '', author: '', publisher: '', stock: 0 })
let editId = null

const loadBooks = async () => {
  const { data } = await bookApi.list()
  books.value = data
}

const handleSearch = async () => {
  if (!searchText.value) {
    loadBooks()
    return
  }
  const { data } = await bookApi.list({ title: searchText.value })
  books.value = data
}

const handleAdd = () => {
  isEdit.value = false
  form.value = { isbn: '', title: '', author: '', publisher: '', stock: 0 }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  editId = row.id
  form.value = { ...row }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (isEdit.value) {
    await bookApi.update(editId, form.value)
    ElMessage.success('修改成功')
  } else {
    await bookApi.create(form.value)
    ElMessage.success('新增成功')
  }
  dialogVisible.value = false
  loadBooks()
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(`确定删除《${row.title}》？`, '提示', { type: 'warning' })
  } catch {
    return
  }
  try {
    await bookApi.delete(row.id)
    ElMessage.success('删除成功')
    loadBooks()
  } catch (err) {
    ElMessage.error('删除失败: ' + (err.response?.data?.message || '该图书可能存在借阅记录'))
  }
}

onMounted(loadBooks)
</script>
