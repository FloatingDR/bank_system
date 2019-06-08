<template>
  <el-menu
    class="aside"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
  >
    <el-submenu v-if="!isAdmin" index="1">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>个人中心</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="1-1" @click="jump('/index/userinfo')">账户信息</el-menu-item>
        <el-menu-item index="1-2" @click="jump('/index/changePassword')">支付密码</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-submenu v-if="!isAdmin" index="2">
      <template slot="title">
        <i class="el-icon-search"></i>
        <span>业务办理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="2-1" @click="jump('/service/save')">存款</el-menu-item>
        <el-menu-item index="2-2" @click="jump('/service/transfer')">取款/转账</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
      <el-menu-item style="font-size: large" v-if="isAdmin" index="3" @click="jump('/admin/cancelCard')">银行卡注销</el-menu-item>
  </el-menu>
</template>

<script>
  export default {
    data() {
      return {
        isAdmin:false,
      }
    },
    methods: {
      jump(url){
        this.$router.push(url)
      },
    },mounted() {
      let role=sessionStorage.getItem("$role");
      if(role=="admin"){
        this.isAdmin=true;
      }else{
        this.isAdmin=false;
      }
    }
  }

</script>

<style scoped>
  .aside {
    height: 100%;
    overflow-x: hidden;
  }
  .el-menu-item{

  }
</style>
