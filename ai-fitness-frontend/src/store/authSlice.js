import { createSlice } from '@reduxjs/toolkit'

const authSlice = createSlice({
  name: 'auth',
  initialState : {
    user: JSON.parse(localStorage.getItem('user')) || null,
    token: localStorage.getItem('token') || null,
    userId: localStorage.getItem('userId') || null,
  },
  reducers: {
    setCredentials: (state, action) => {
      state.user = action.payload.user;
    },
    logout: (state) => {
    
    },
  },
})

export const { setCredentials, logout} = authSlice.actions
export default authSlice.reducer