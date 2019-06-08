<template>
  <!--修改支付密码-->
  <div class="accoutChange">
    <el-form class="regist" style="border-right: 1px;">
      <el-form-item label="身份证号码">
        <el-input v-model="modifyInfo.identityCardId" clearable></el-input>
      </el-form-item>
      <el-form-item label="银行卡号码">
        <el-input v-model="modifyInfo.bankCardId" clearable></el-input>
      </el-form-item>
      <el-form-item label="旧支付密码">
        <el-input placeholder="输入旧的6-11位数字密码" v-model="modifyInfo.oldPassword" show-password clearable></el-input>
      </el-form-item>
      <el-form-item label="新支付密码">
        <el-input placeholder="输入新的6-11位数字密码" v-model="modifyInfo.newPassword" show-password clearable></el-input>
      </el-form-item>
      <div style="width: 100%">
        <el-button class="registerButton" type="info" @click="changePass">确 定</el-button>
      </div>
    </el-form>

  </div>
</template>

<script>
  import link from '@/api/card'

  export default {
    data() {
      return {
        modifyInfo: {
          identityCardId: "",
          bankCardId:"",
          oldPassword: "",
          newPassword: ""
        }
      }
    }, methods: {
      changePass() {
        let that = this;
        if (that.judgeInfo() == false) {
          return;
        }
        link.changePayPassword(that.modifyInfo).then(function (resp) {
          if (resp.data.code == 200) {
            that.$message({
              message: "修改成功",
              type: 'success'
            })
            return;
          } else {
            that.showWarningMessage(resp.data.data);
            return;
          }
        })
      },
      judgeInfo() {
        let info = this.modifyInfo;
        let that = this;
        if (info.identityCardId == null || info.identityCardId == "") {
          that.showWarningMessage("身份证不能为空");
          return false;
        } else {
          info.identityCardId = info.identityCardId.toUpperCase();
          let identFormat = /^([0-9]{18})|([0-9]{17}X)$/
          if (!identFormat.test(info.identityCardId)) {
            that.showWarningMessage("身份证不正确");
            return false;
          }
        }
        if (info.oldPassword == null || info.oldPassword == "") {
          that.showWarningMessage("旧密码不能为空");
          return false;
        } else {
          let passwordFormat = /^[0-9]{6,11}$/
          if (!passwordFormat.test(info.oldPassword)) {
            that.showWarningMessage("旧密码格式错误");
            return false;
          }
        }
        if (info.newPassword == null || info.newPassword == "") {
          that.showWarningMessage("新密码不能为空");
          return false;
        } else {
          if (info.oldPassword == info.newPassword) {
            that.showWarningMessage("新密码与旧密码相同");
            return false;
          }
          let passwordFormat = /^[0-9]{6,11}$/
          if (!passwordFormat.test(info.newPassword)) {
            that.showWarningMessage("新密码格式错误");
            return false;
          }
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
  .accoutChange {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    flex-direction: column;
  }

  .registerButton {
    background-color: darkgray;
    float: right;
  }

  .regist {
    display: flex;
    align-items: center;
    flex-direction: column;
    margin-top: 5%;
  }

  .el-form-item {
    display: inline-flex;
    float: right;
  }
</style>
