<template>
  <div class="accoutChange">
    <el-form class="regist" style="border-right: 1px;">
      <el-form-item label="银行卡号">
        <el-select v-model="save.bankCardId" placeholder="请选择">
          <el-option
            v-for="item in options2"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="支付密码">
        <el-input placeholder="请输入6-11为支付密码" v-model="save.password" show-password clearable></el-input>
      </el-form-item>
      <el-form-item label="交易类型">
        <el-select v-model="save.transType" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item v-if="save.transType=='转账'" label="转入账号">
        <el-input placeholder="对方银行卡账号" v-model="save.receiptCard" clearable></el-input>
      </el-form-item>
      <el-form-item label="交易金额">
        <el-input v-model="save.money" clearable></el-input>
      </el-form-item>
      <div style="width: 100%">
        <el-button class="registerButton" type="info" @click="saveMoney">确 定</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
  import link from '@/api/service'
  export default {
    data() {
      return {
        save: {
          bankCardId: "",
          password: "",
          transType: "",
          money: "",
          receiptCard: ""
        },
        options: [{
          value: '定期取款',
          label: '定期取款'
        }, {
          value: '取款',
          label: '取款'
        }, {
          value: '转账',
          label: '转账'
        }],
        options2: [],
      }
    },
    methods: {
      saveMoney() {
        if (!this.judgeInfo()) {
          return;
        }
        let that = this;
        link.drawMoney(that.save).then(function (resp) {
          console.log(that.save)
          if (resp.data.code == 200) {
            that.$message({
              message: "交易成功!",
              type: "success"
            });
            that.save.receiptCard = "";
            that.save.bankCardId = "";
            that.save.transType = "";
            that.save.password = "";
            that.save.money = "";
            return;
          } else {
            that.showWarningMessage(resp.data.data);
          }
        })
      },
      judgeInfo() {
        let saveInfo = this.save;
        let that = this;
        if (saveInfo.password == "" || saveInfo.password == null) {
          that.showWarningMessage("密码不能为空!");
          return false;
        }
        if (saveInfo.bankCardId == "" || saveInfo.bankCardId == null) {
          that.showWarningMessage("银行卡号不能为空!");
          return false;
        }
        if (saveInfo.transType == "" || saveInfo.transType == null) {
          that.showWarningMessage("交易类型不能为空!");
          return false;
        } else if (saveInfo.transType == "转账") {
          if (saveInfo.receiptCard == "" || saveInfo.receiptCard == null) {
            that.showWarningMessage("转款账号不能为空!");
            return false;
          }
          let num = /^[1-9][0-9]{18}$/
          if (!num.test(saveInfo.receiptCard)) {
            that.showWarningMessage("转款账号不对!");
            return false;
          }
        }
        let passwordFormat = /^[0-9]{6,11}$/
        if (!passwordFormat.test(saveInfo.password)) {
          that.showWarningMessage("密码格式错误!");
          return false;
        }
        if (saveInfo.money == "" || saveInfo.money == null) {
          that.showWarningMessage("金额不能为空!");
          return false;
        }
        let moneyFormat = /^([0-9]+)(.[0-9]+)?$/
        if (!moneyFormat.test(saveInfo.money)) {
          that.showWarningMessage("金额格式错误!");
          return false;
        }
        return true;
      }, showWarningMessage(val) {
        this.$message({
          showClose: true,
          message: val,
          type: "warning"
        });
      },
    },
    mounted() {
      let idCard = sessionStorage.getItem("$user");
      let that = this;
      link.getBankCard(idCard).then(function (resp) {
        if (resp.data.code == 200) {
          let idCards = resp.data.data;
          for (let i = 0; i < idCards.length; i++) {
            let temp = {
              value: '',
              label: ''
            }
            temp.value=idCards[i];
            temp.label=idCards[i];
            that.options2.push(temp);
          }
        }
      })
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
