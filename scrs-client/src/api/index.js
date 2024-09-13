import axios from "axios";

const backendUrl = "http://localhost:8080";

console.log(backendUrl);

export default async function getUsers() {
  return axios.get(backendUrl + "/user/get-all");
}
