<template>
  <el-dialog
    title="注册"
    :visible.sync="registDiaglog"
    width="30%"
  >
    <el-form class="regist">
      <el-form-item label="身份证号">
        <el-input v-model="regist.identityCardId" placeholder="将做为你的登陆账号" clearable></el-input>
      </el-form-item>
      <el-form-item label="真实姓名">
        <el-input v-model="regist.username" clearable></el-input>
      </el-form-item>
      <el-form-item label="登陆密码">
        <el-input placeholder="输入6-11位数字密码" v-model="regist.loginPassword" show-password clearable></el-input>
      </el-form-item>
      <el-form-item label="支付密码">
        <el-input placeholder="支付密码请勿与登陆密码相同" v-model="regist.payPassword" show-password clearable></el-input>
      </el-form-item>
      <el-form-item label="家庭地址">
        <el-input v-model="regist.address" clearable></el-input>
      </el-form-item>
      <el-form-item label="联系方式">
        <el-input v-model="regist.telephone" clearable></el-input>
      </el-form-item>
      <el-form-item label="开户金额">
        <el-input v-model="regist.openMoney" clearable></el-input>
      </el-form-item>
    </el-form>
    <div class="registDiaglogFooter" slot="footer">
      <el-button class="registerButton" type="info" @click="registDiaglog=false">取 消</el-button>
      <el-button class="registerButton" type="info" @click="doRegist">确 定</el-button> 
    </div>
  </el-dialog>
</template>

<script>
  import registLink from '@/api/login'
  export default {
    data() {
      return {
        registDiaglog:false,
        regist: {
          identityCardId: "",
          username: "",
          address: "",
          telephone: "",
          loginPassword: "",
          payPassword: "",
          openMoney: ""
        },
      }
    },
    methods:{
      doRegist(){
        let that=this;
        if(that.judgeInfo()==false){
          return;
        }
        registLink.regist(that.regist).then(function (resp) {
          if(resp.data.code==200){
            that.$alert(resp.data.data, '卡号', {
              confirmButtonText: '确定'
              }
            );
            that.registDiaglog=false;
          }else if(resp.data.code==403) {
            that.showWarningMessage("禁止 ，您所办理的银行卡超过三张！");
            return;
          }else{
            that.showWarningMessage("开户失败");
            return;
          }
        })
      },
      judgeInfo(){
        let info=this.regist;
        let that=this;
        if(info.openMoney==null||info.openMoney==""){
          that.showWarningMessage("开户金额不能为空");
          return false;
        }
        let moneyFormat=/^([0-9]+)$/
        if(!moneyFormat.test(info.openMoney)){
          that.showWarningMessage("金额格式错误");
          return false;
        }
        if(info.identityCardId==null||info.identityCardId==""){
          that.showWarningMessage("身份证不能为空");
          return false;
        }else{
          info.identityCardId=info.identityCardId.toUpperCase();
          let identFormat=/^([0-9]{18})|([0-9]{17}X)$/
          if(!identFormat.test(info.identityCardId)){
            that.showWarningMessage("身份证不正确");
            return false;
          }
        }
        if(info.username==null||info.username==""){
          that.showWarningMessage("姓名不能为空");
          return false;
        }
        if(info.loginPassword==null||info.loginPassword==""){
          that.showWarningMessage("密码不能为空");
          return false;
        }else{
          let passwordFormat=/^[0-9]{6,11}$/
          if(!passwordFormat.test(info.loginPassword)){
            that.showWarningMessage("登陆密码格式错误");
            return false;
          }
        }
        if(info.payPassword==null||info.payPassword==""){
          that.showWarningMessage("支付密码不能为空");
          return false;
        }else{
          if(info.payPassword==info.loginPassword){
            that.showWarningMessage("登陆密码与支付密码冲突");
            return false;
          }
          let passwordFormat=/^[0-9]{6,11}$/
          if(!passwordFormat.test(info.payPassword)){
            that.showWarningMessage("支付密码格式错误");
            return false;
          }
        }
        if(info.telephone==null||info.telephone==""){
          that.showWarningMessage("姓名不能为空");
          return false;
        }if(info.address==null||info.address==""){
          that.showWarningMessage("家庭地址不能为空");
          return false;
        }

      },
      showWarningMessage(val) {
        this.$message({
          showClose: true,
          message: val,
          type: "warning"
        });
      },
    }
  }
</script>

<style scoped>
  .registDiaglogFooter {
    display: inline-flex;
  }

  .registerButton {
    background-color: darkgray;
  }
  .regist {
    display: flex;
    align-items: center;
    flex-direction: column;
  }
  .el-form-item{
    display: inline-flex;
  }
</style>
