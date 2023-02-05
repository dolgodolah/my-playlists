import * as queryString from "querystring";
import axios from "axios";

export interface JsonResponse extends Record<string, any> {
  statusCode: number
}

export default function useClient() {
  function get(url: string, params: Record<string, any> = {}): Promise<JsonResponse> {
    const request = appendQueryParams(url, params)
    return axios.get(request, {
      headers: {
        'Content-Type': 'application/json'
      }
    }).then((res) => {
      return res.data
    })
  }

  function post(url: string, params: Record<string, any> = {}): Record<string, any> {
    const request = appendQueryParams(url, params)
    return axios.post(request, { ...params }, {
      headers: {
        'Content-Type': 'application/json'
      }
    }).then((res) => {
      return res.data
    })
  }

  return { get, post }
}

function appendQueryParams(url: string, params: Record<string, any> = {}): string {
  if (!url || !params) {
    return url
  }

  const sep = url.includes('?') ? '&' : '?'
  return `${url}${sep}${queryString.stringify(params)}`
}