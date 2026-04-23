<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span style="font-size: 18px; font-weight: bold;">读者管理</span>
          <el-button type="success" @click="handleAdd">新增读者</el-button>
        </div>
      </template>

      <el-table :data="readers" stripe style="width: 100%">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="name" label="姓名" width="150" />
        <el-table-column prop="phone" label="电话" width="160" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑读者' : '新增读者'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="姓名">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" />
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
import { readerApi } from '../api'

const readers = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const form = ref({ name: '', phone: '', email: '' })
let editId = null

const loadReaders = async () => {
  const { data } = await readerApi.list()
  readers.value = data
}

const handleAdd = () => {
  isEdit.value = false
  form.value = { name: '', phone: '', email: '' }
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
    await readerApi.update(editId, form.value)
    ElMessage.success('修改成功')
  } else {
    await readerApi.create(form.value)
    ElMessage.success('新增成功')
  }
  dialogVisible.value = false
  loadReaders()
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(`确定删除读者「${row.name}」？`, '提示', { type: 'warning' })
  } catch {
    return
  }
  try {
    await readerApi.delete(row.id)
    ElMessage.success('删除成功')
    loadReaders()
  } catch (err) {
    ElMessage.error('删除失败: ' + (err.response?.data?.message || '该读者可能存在借阅记录'))
  }
}

onMounted(loadReaders)
</script>
