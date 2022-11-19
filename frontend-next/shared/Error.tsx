import StatusCode from "./StatusCode";

const alertError = (response: any) => {
  if (response.statusCode == StatusCode.NOT_AUTHORIZED) {
    window.location.href = "/login";
  }
  else if (response.message) {
    alert(response.message)
  } else {
    alert("500 error")
  }
}

export default alertError;