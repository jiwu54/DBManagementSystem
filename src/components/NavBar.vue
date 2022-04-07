<template>
  <div>
    <el-menu
      style="width: 200px; min-height: calc(100vh - 50px)"
      default-active="user"
      :default-openeds="[1]"
      class="el-menu-vertical-demo"
    >
      <el-sub-menu index="1">
        <template #title>
          <span>System</span>
        </template>
          <el-menu-item index="user">User Management</el-menu-item>
  
      </el-sub-menu>
       <el-menu-item index="data">Data Management</el-menu-item>
    </el-menu>
  </div>
</template>

<script >

</script>

<style></style>
