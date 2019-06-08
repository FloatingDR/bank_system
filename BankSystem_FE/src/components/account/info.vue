<template>
  <el-main class="main">
    <el-table
      :data="tableData"
      style="width: 100%;">
      <el-table-column
        prop="bankCardId"
        label="银行卡号"
      >
      </el-table-column>
      <el-table-column
        prop="identityCardId"
        label="身份证"
      >
      </el-table-column>
      <el-table-column
        prop="username"
        label="姓名"
      >
      </el-table-column>
      <el-table-column
        prop="address"
        label="地址"
      >
      </el-table-column>
      <el-table-column
        prop="telephone"
        label="电话"
      >
      </el-table-column>

      <el-table-column
        prop="openDate"
        label="开户时间"
      >
      </el-table-column>
      <el-table-column
        prop="openMoney"
        label="开户金额"
      >
      </el-table-column>
      <el-table-column
        prop="currentBalance"
        label="活期余额"
      >
      </el-table-column>
      <el-table-column
        prop="regularBalance"
        label="定期余额"
      >
      </el-table-column>
      <el-table-column width="180">
        <template slot-scope="scope" style="display: flex;flex-direction: column">

          <el-button
            type="info"
            size="mini"
            @click="handle2(scope.$index, scope.row)">交易记录
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-card class="box-card" v-if="showBill">
      <div slot="header" class="clearfix">
        <span style="float: left">交易记录</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="closeBill">关闭</el-button>
      </div>
      <div style="width: 100%">
        <el-collapse v-model="activeNames" accordion>
          <div v-for="item in billData">
            <el-collapse-item :title="item.transType" >
              <p class="billP">金额:{{item.transMoney}}</p>
              <p class="billP">时间:{{item.tradeDate}}</p>
              <p class="billP" v-if="item.receiptCard">目标账户:{{item.receiptCard}}</p>
            </el-collapse-item>
          </div>
        </el-collapse>
      </div>
    </el-card>
  </el-main>

</template>

<script>
  import cardLink from '@/api/card'

  export default {
    data() {
      return {
        isAdmin:false,
        showBill:false,
        i: 1,
        activeNames: '1',
        idCard: "111",
        tableData: [],
        billData: []
      }
    },
    methods: {
      handleChange(val) {
      },
      handle1(index, row) {
        if (this.cancelCard(row.bankCardId)) {
          this.tableData.splice(index, 1);
        }
        return;
      },
      closeBill(){
        this.showBill=false;
      },
      handle2(index, row) {
        let that = this;
        that.billData=[];
        cardLink.billLog(row.bankCardId).then(function (resp) {
          if (resp.data.code == 200) {
            for(let i=0;i<resp.data.data.savingLog.length;i++){
              that.billData.push(resp.data.data.savingLog[i]);
            }
            for(let i=0;i<resp.data.data.drawLong.length;i++){
              that.billData.push(resp.data.data.drawLong[i]);
            }
            that.showBill=true;
          } else {
            that.showBill=true;
          }
        })
      },
    },
    mounted() {
      let id=sessionStorage.getItem("$user");
      let role=sessionStorage.getItem("$role");
      if(role=="admin"){
        this.isAdmin=true;
      }else{
        this.isAdmin=false;
      }
      let that=this;
      cardLink.cardInfo(id).then(function (resp) {
        if(resp.data.code==200){
          that.tableData=resp.data.data;
        }else{
          that.$message({
            message:"网络错误",
            type:"warning"
          })
        }
      })
    }
  }
</script>

<style scoped>
  .main {
    width: 100%;
  }

  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }
  .billP{
    text-align: left;
  }
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .el-collapse-item{
    width: 100%;
    display: flex;
    flex-direction: column;
  }
  .clearfix:after {
    clear: both
  }

  .clearfix {
    width: 100%;
  }

  .box-card {
    margin-top: 1%;
    width: 100%;
    /*display: flex;*/
    /*flex-direction: column;*/
    /*align-items: flex-start;*/
  }
</style>
