import { createContext, useState } from "react";

//1. Context 생성
export const CartContext = createContext();

//2. CartProvider는 CartContext를 사용하는 컴포넌트들의 범위를 정의
//이 범위에 속한 컴포넌트들은, cartCount를 쓸수있다. (value값으로 보낸것만 사용가능)
export const CartProvider = ({ children }) => { 
    const [cartCount, setCartCount] = useState(0); //장바구니 수량 관리
    const [cartList, setCartList] = useState([]); //장바구니 리스트
    const [totalPrice, setTotalPrice] = useState(0); //장바구니 상품 전체 가격

    //handling 하는애가 Provider
    return (
        <CartContext.Provider value={{ cartCount, setCartCount, cartList, setCartList, totalPrice, setTotalPrice }}>  {/* 정의된 변수들을 value로 return */}
            {children}
        </CartContext.Provider>
    );
}
