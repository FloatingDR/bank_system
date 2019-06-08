import {SERVER_BASE,link} from "@/api/config";
export default {
  cardInfo(identityCardId	){
    return link.get(SERVER_BASE+"/query_info/user_base_info/"+identityCardId)
  },
  cancelCard(bankCardId,payPassword){
    return link.post(SERVER_BASE+"/bank_card/delete_bankcard",{
      bankCardId:bankCardId,
      payPassword:payPassword
    })
  },
  billLog(bankCardId){
    return link.get(SERVER_BASE+"/query_info/bill_log/"+bankCardId);
  },
  changePayPassword(user){
    return link.post(SERVER_BASE+"/bank_card/change_bankcard_password",{
      identityCardId:user.identityCardId,
      bankCardId:user.bankCardId,
      oldPassword:user.oldPassword,
      newPassword:user.newPassword
    })
  }
}
