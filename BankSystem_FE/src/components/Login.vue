<template>
  <div class="login">
    <el-header class="title"  height="7%">
      <span>超</span>
      <span>帅</span>
      <span>银</span>
      <span>行</span>
    </el-header>
    <el-form class="loginform">
      <p style="width: 100%;font-size: 30px">登陆</p>
      <p style="margin-top: 10%;color: red;height: 2%;margin-bottom: 10% ">{{message}}</p>
      <i class="el-icon-user"></i>
      <el-input v-model="login.identityCardId" placeholder="Username" clearable></el-input>
      <p></p>
      <i class="el-icon-key"></i>
      <el-input v-model="login.loginPassword" placeholder="Password" show-password clearable
                @keyup.enter.native="doLogin"></el-input>
      <p class="regist">
        <el-checkbox v-model="autoLogin" style="float:left;margin-left: 20%">自动登陆</el-checkbox>
        <el-link style="margin-left: 10%;margin-right: 10px;font-size: 13px" @click="showChangeDiaglog">忘记密码?</el-link>
        <el-link @click="showRegister">注册</el-link>
      </p>
      <el-button class="loginButton" type="info" round @click="doLogin">登陆</el-button>
    </el-form>

    <!--    脚注解-->
    <router-view name="footer"/>

    <!--    注册对话框-->
    <router-view name="register"/>

    <!--    修改密码-->
    <router-view name="changePassword"/>
  </div>

</template>

<script>
  import loginLink from '@/api/login'

  export default {
    data() {
      return {
        message:"",
        autoLogin: false,
        login: {
          identityCardId: "",
          loginPassword: ""
        },
      }
    },
    methods: {
      showRegister(){
        this.$children[3]._data.registDiaglog=true;
      },
      showChangeDiaglog(){
        this.$children[4]._data.changeDiaglog=true;
      },
      showWarningMessage(val) {
        this.$message({
          showClose: true,
          message: val,
          type: "warning"
        });
      },
      doLogin() {
        // sessionStorage.setItem("$token", "1");
        // this.$router.push("/index");
        // return;
        let that = this;
        // that.login.identityCardId=that.login.identityCardId.toUpperCase();
        if (that.login.loginPassword == "" || that.login.identityCardId == "" || that.login.identityCardId == null || that.login.identityCardId == null) {
          that.message="用户名或密码不能为空!"
          return;
        }
        // let idFormat=/^[1-9][0-9]{16}([0-9]|[X])$/;
        // if(!idFormat.test(that.login.identityCardId)){
        //   that.message="请输入正确的身份证号!"
        //   return;
        // }
        let passFormat=/^[0-9]{6,11}$/;
        if(!passFormat.test(that.login.loginPassword)){
          that.message="请输入6-11位数字密码!"
          return;
        }
        that.message="";
        loginLink.login(that.login).then(function (resp) {
          if (resp.data.code == 200) {
            sessionStorage.setItem('$token', resp.data.msg);
            sessionStorage.setItem('$user',that.login.identityCardId);
            sessionStorage.setItem('$role', resp.data.data.roleStyle);
            that.$router.push("/index");
          } else if (resp.data.code == 404 || resp.data.code == 403) {
            that.message="用户名或密码错误!";
          } else {
            that.showWarningMessage("网络错误");
          }
        })
      },
    }
  }
</script>

<style scoped>
  .title{
    width: 100%;
    font-size: 30px;
    margin-top: 1%;
  }
  span{
    font-family: 微软雅黑 Light;
    font-size: 40px;
    margin-right: 2%;
    margin-left: 2%;
  }
  .login {
    height: 100%;
    width: 100%;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: center;
    /*align-items: center;*/
  }

  .loginform {
    background-color: ghostwhite;
    box-shadow: 2px 2px 5px black;
    border-radius: 5px;
    height: 400px;
    width: 340px;
    flex-direction: row;
    flex-wrap: wrap;
  }

  .el-icon-user {
    width: 10%;
  }

  .el-input {
    width: 70%;
    flex: 1;
  }

  .el-icon-key {
    width: 10%;
  }

  .loginButton {
    align-self: flex-end;
    background-color: black;
    width: 70%;
    margin-top: 10%;
    margin-left: 10%;
  }
  .regist{
    margin-right: 10%;
  }

</style>
