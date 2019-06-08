<template>
    <div class="index">
        <el-header height="7%" class="title" style="text-align: left">假装这里是个很好看的   LOGO</el-header>
        <el-aside width="10%" class="aside">
          <router-view name="aside"/>
        </el-aside>
        <el-container style="display: flex; flex-direction: column;width: 85%">
          <router-view name="header"/>
          <router-view  name="main"/>
        </el-container>
      <router-view name="footer"/>
    </div>
</template>

<script>
    export default {
        name: "index"
    }
</script>

<style scoped>
  .index{
    height: 100%;
    width: 100%;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: center;
    /*align-items: center;*/
  }
  .title{
    width: 100%;
    font-family: '微软雅黑 Light';
    font-size: 20px;
    text-align: center;
    line-height: 60px;
  }

  .aside{
    height: 88%;
  }

</style>
