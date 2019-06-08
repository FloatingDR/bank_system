import {SERVER_BASE,link} from "@/api/config";
export default {
  login(user){
    return link.post(SERVER_BASE+"/auth/login",{
      identityCardId:user.identityCardId,
      loginPassword:user.loginPassword
    })
  },
  regist(register){
    return link.post(SERVER_BASE+'/auth/register_account',{
      identityCardId:register.identityCardId,
      username:register.username,
      address:register.address,
      telephone:register.telephone,
      loginPassword:register.loginPassword,
      payPassword:register.payPassword,
      openMoney:register.openMoney
    })
  },
  changPassword(user){
    return link.post(SERVER_BASE+'/auth/change_login_password',{
      identityCardId:user.identityCardId,
      oldPayPassword:user.oldPayPassword,
      payPassword:user.payPassword
    })
  }
}
