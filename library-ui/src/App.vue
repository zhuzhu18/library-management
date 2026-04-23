<template>
  <el-container style="height: 100vh">
    <el-aside width="200px" style="background-color: #304156">
      <div style="color: #fff; text-align: center; padding: 20px 0; font-size: 18px; font-weight: bold;">
        图书管理系统
      </div>
      <el-menu
        :default-active="$route.path"
        router
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409eff"
      >
        <el-menu-item index="/books">
          <el-icon><Reading /></el-icon>
          <span>图书管理</span>
        </el-menu-item>
        <el-menu-item index="/readers">
          <el-icon><User /></el-icon>
          <span>读者管理</span>
        </el-menu-item>
        <el-menu-item index="/borrows">
          <el-icon><List /></el-icon>
          <span>借阅管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main style="background-color: #f0f2f5; padding: 20px;">
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup>
</script>

<style>
* { margin: 0; padding: 0; }
body { font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Microsoft YaHei', sans-serif; }
</style>
