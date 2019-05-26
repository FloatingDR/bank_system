<template>
  <div style="margin-top: 5%">
    <span>银行卡号:</span>
    <el-input style="width: 20%" v-model="bankCardId"></el-input>
    <i class="el-icon-warning-outline" style="color:#ff0000;"></i>

    <el-button type="info" @click="cancelCard">确定</el-button>
  </div>
</template>

<script>
  import cardLink from '@/api/card'
  export default {
    data() {
      return {
        bankCardId: "",
      }
    },
    methods: {
      cancelCard() {
        let that = this;
        this.$prompt('请输入支付密码', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /^[0-9]{6,11}$/,
          inputErrorMessage: '密码格式错误'
        }).then(({value}) => {
          cardLink.cancelCard(that.bankCardId, value).then(function (resp) {
            if (resp.data.code == 200) {
              that.$message({
                message: "注销成功",
                type: "success"
              })
              return true;
            } else {
              that.$message({
                message: resp.data.data,
                type: "warning"
              })
              return false;
            }
          })
        }).catch(() => {
          return false;
        });
      }
    }
  }
</script>

<style scoped>

</style>
