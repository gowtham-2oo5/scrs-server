function encodeToken(token) {
  // Use Base64 encoding to encode the token
  return btoa(token);
}

// Decode a token (e.g., when retrieving from sessionStorage)
function decodeToken(encodedToken) {
  // Decode the Base64 encoded token
  return atob(encodedToken);
}

export { encodeToken, decodeToken };
