import { createSlice } from '@reduxjs/toolkit'

export const authSlice = createSlice({
  name: 'auth',
  initialState: {
    isLogin: false
  },
  reducers: {
    login(state, action) {
        state.isLogin = !state.isLogin;
        const { userId } = action.payload;
        const loginInfo = {"token": "123455dkfdf", "userId": userId};
        localStorage.setItem("loginInfo", JSON.stringify(loginInfo));

    },
    logout(state) {
        state.isLogin = !state.isLogin;
        localStorage.removeItem("loginInfo");
    }
  }
})

// Action creators are generated for each case reducer function
export const { login, logout } = authSlice.actions //컴포넌트 또는 API에서 호출(dispatch)

export default authSlice.reducer //store에서 import하는 기준