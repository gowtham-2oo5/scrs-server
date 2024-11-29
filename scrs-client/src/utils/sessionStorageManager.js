import { encodeToken, decodeToken } from "./EncodeDecode";

const setItems = (data) => {
  console.log(`Received data: ${data}`);
  console.log(`Received data of Data: ${data.data}`);
  sessionStorage.setItem("token", encodeToken(data.token));
  if (data.data.superAdmin) {
    console.log("Super Admin");
    sessionStorage.setItem("role", encodeToken("SUPER-ADMIN"));
  } else sessionStorage.setItem("role", encodeToken(data.role));
  sessionStorage.setItem("dp", encodeToken(data.data.profilePicture));
  sessionStorage.setItem("name", encodeToken(data.data.name));
};

const clearItems = () => {
  sessionStorage.clear();
};

const getSessionItem = (itemName) => {
  console.log(`Getting item: ${itemName}`);
  const res = decodeToken(sessionStorage.getItem(itemName));
  console.log(`Got string: ${res}`);
  return res;
};

export { setItems, clearItems, getSessionItem };
