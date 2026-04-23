import axios from 'axios'

const http = axios.create({ baseURL: '/api' })

export const bookApi = {
  list: (params) => http.get('/books', { params }),
  get: (id) => http.get(`/books/${id}`),
  create: (data) => http.post('/books', data),
  update: (id, data) => http.put(`/books/${id}`, data),
  delete: (id) => http.delete(`/books/${id}`)
}

export const readerApi = {
  list: () => http.get('/readers'),
  get: (id) => http.get(`/readers/${id}`),
  create: (data) => http.post('/readers', data),
  update: (id, data) => http.put(`/readers/${id}`, data),
  delete: (id) => http.delete(`/readers/${id}`)
}

export const borrowApi = {
  list: (params) => http.get('/borrows', { params }),
  borrow: (data) => http.post('/borrows', data),
  returnBook: (id) => http.put(`/borrows/${id}/return`)
}
