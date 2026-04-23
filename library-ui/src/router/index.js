import { createRouter, createWebHistory } from 'vue-router'
import BookView from '../views/BookView.vue'
import ReaderView from '../views/ReaderView.vue'
import BorrowView from '../views/BorrowView.vue'

const routes = [
  { path: '/', redirect: '/books' },
  { path: '/books', name: 'Books', component: BookView },
  { path: '/readers', name: 'Readers', component: ReaderView },
  { path: '/borrows', name: 'Borrows', component: BorrowView }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
