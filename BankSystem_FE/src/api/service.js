import {SERVER_BASE,link} from "@/api/config";
export default {
  saveMoney(save) {
    return link.post(SERVER_BASE+'/access_money/saving_money',{
      bankCardId:save.bankCardId,
      password:save.password,
      savingType:save.savingType,
      money:save.money,
      regularMonth:save.regularMonth
    })
  },
  drawMoney(save) {
    return link.post(SERVER_BASE+'/access_money/draw_money',{
      bankCardId:save.bankCardId,
      payPassword:save.password,
      transInfo:{
        transType:save.transType,
        transMoney:save.money,
        receiptCard:save.receiptCard
      }
    })
  },
  getBankCard(idCard){
    return link.get(SERVER_BASE+'/bank_card/get_bank_cards/'+idCard);
  }
}
