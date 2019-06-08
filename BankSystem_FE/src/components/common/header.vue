<template>
  <el-header height="8%" class="header">
    <el-link icon="el-icon-arrow-right" type="info" class="index" @click="toIndex">首页</el-link>
    <el-link icon="el-icon-user" type="info" class="info" @click="logout">退出登陆</el-link>
  </el-header>
</template>

<script>
    export default {
        data(){
          return{

          }
        },
      methods:{
        logout(){
          this.$confirm('确认退出?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$router.push("/")
          })
        },
        toIndex(){
          this.$router.push("/index")
        }
      }
    }
</script>

<style scoped>
  .header{
    width: 100%;
    background-color: gray;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }
  .index{
    font-size: x-large;
    color: white;

  }
  .info{
    font-size: large;
    color: white;
    margin-right: 2%;
  }
</style>
