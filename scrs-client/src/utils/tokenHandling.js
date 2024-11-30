import { jwtDecode } from "jwt-decode";
export const getTokenData = (token) => {
  const res = jwtDecode(token);
  // console.log(res);
  return res;
};
