import axios from "axios"
const SERVER_BASE="http://192.168.1.199:8080/api"

let link=axios.create()
link.interceptors.request.use(config => {
  if (sessionStorage.$token) {
    config.headers = { 'Authorization': sessionStorage.$token }
  }
  return config
}, err => {
  return Promise.reject(err)
})
link.interceptors.response.use((data) => {
  return data
}, err => {
  Message.warning("发生错误")
  return Promise.reject(err)
})
export {
  SERVER_BASE,
  link
}
