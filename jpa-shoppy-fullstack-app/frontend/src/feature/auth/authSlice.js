import { createSlice } from '@reduxjs/toolkit'

const saveAuth = JSON.parse(localStorage.getItem("auth"));

export const authSlice = createSlice({
  name: 'auth',
  initialState: saveAuth || {
    isLogin: false,
    role: ''
  },
  reducers: {
    login(state, action) {
        state.isLogin = !state.isLogin;
        const { userId, role } = action.payload;
        const loginInfo = {"userId": userId};
        state.role = role;

        localStorage.setItem("loginInfo", JSON.stringify(loginInfo)); //여기서 String타입으로 저장하기때문에 API에서 다시 parsing 해야함

        //새로고침을 위한 데이터 복사(localStorage 저장)
        localStorage.setItem("auth",
            JSON.stringify({
//                "isLogin" : isLogin //이름이 같은 경우 한쪽을 생략해도 된다.
                isLogin: true,
                userId,
                role
            })
        )


    },
    logout(state) {
        state.isLogin = !state.isLogin;
        localStorage.removeItem("loginInfo");
        localStorage.removeItem("auth");
        localStorage.removeItem("cart");
    }
  }
})

// Action creators are generated for each case reducer function
export const { login, logout } = authSlice.actions //컴포넌트 또는 API에서 호출(dispatch)

export default authSlice.reducer //store에서 import하는 기준