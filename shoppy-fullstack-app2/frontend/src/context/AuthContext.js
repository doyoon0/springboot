import { createContext, useState } from "react";

//1. AuthContext 생성
export const AuthContext = createContext();

//2. AuthContext의 Provider 생성 
export const AuthProvider = ({children}) => {
    const [isLogin, setIsLogin] = useState(false);

    return (
        <AuthContext.Provider value={{isLogin, setIsLogin}}>
            {children}
        </AuthContext.Provider>
    );
}
