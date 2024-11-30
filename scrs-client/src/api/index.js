import { getSessionItem } from "@/utils/sessionStorageManager";

// Utility function to get the token
export const getToken = () => {
  console.log("CALLED GET TOKEN");
  return getSessionItem("token");
};
