import { createSlice } from '@reduxjs/toolkit'
import { cartItemsCheck, cartItemsAddInfo } from '../../utils/cart.js';

const initialState = {
    cartCount: 0,
    cartList: [],
    totalPrice: 0
}

export const cartSlice = createSlice({
  name: 'cart',
  initialState, 
  reducers: {
    addCartItem (state, action) { //현재 상태인 cartCount 값, 컴포넌트에서 발생하는 evet = action
        const {cartItem} = action.payload;
        state.cartList = cartItemsCheck(state.cartList, cartItem); //init에게 state(기존값: [])에 추가하라고 명시
    },

    showCartItem (state, action) {
        const { items } = action.payload;
        state.cartList = cartItemsAddInfo(items, state.cartList)
    },

    updateCartCount (state) {
        state.cartCount = state.cartList.reduce((total, item) => total + item.qty, 0 );
    },
    
    updateTotalPrice (state) {
        state.totalPrice = state.cartList.reduce((total, item) => total + (item.qty * item.price), 0);
    },

    updateCartItem (state, action) {
        const { cid, type } = action.payload;
        //카트 수량 변경
        state.cartList = state.cartList.map((item) =>
            item.cid === cid ? //cid 같은지 체크(1)
                type === '+' ? { ...item, qty: item.qty + 1 } : //type이 +/- 체크(2)
                    item.qty > 1 ? //qty가 1보다 큰지 체크(3)
                        { ...item, qty: item.qty - 1 } :
                        item
                : item
        );
    },

    removeCartItem (state, action) {
        const {cid} = action.payload;
        state.cartList = state.cartList.filter(item => !(item.cid === cid)); //삭제한것 제외하고 전부 
    }
  },
})

// Action creators are generated for each case reducer function
export const { addCartItem, updateCartCount, showCartItem, updateTotalPrice, updateCartItem, removeCartItem } = cartSlice.actions //컴포넌트 또는 API에서 호출(dispatch)

export default cartSlice.reducer //store에서 import하는 기준