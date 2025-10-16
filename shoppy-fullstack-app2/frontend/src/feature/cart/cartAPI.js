import React from 'react';
import { addCartItem, updateCartCount, showCartItem, updateTotalPrice, updateCartItem, removeCartItem } from './cartSlice.js';
import { axiosData } from '../../utils/dataFetch.js';

export const removeCart = (cid) => async (dispatch) => {
    dispatch(removeCartItem({"cid": cid}));
    dispatch(updateTotalPrice());
    dispatch(updateCartCount());
}

export const updateCart = (cid, type) => async (dispatch) => {
    dispatch(updateCartItem({ "cid": cid, "type": type })); //slice에서 item으로 구조분해할당
    dispatch(updateTotalPrice());
    dispatch(updateCartCount());

}

export const showCart = () => async (dispatch) => {
    // 여기서는 Slice에 있는 cartList에 접근할수가 없기때문에 items라는 이름을 붙여 Slice로 넘긴다
    const jsonData = await axiosData('/data/products.json');
    dispatch(showCartItem({ "items": jsonData }));
    dispatch(updateTotalPrice());
}

// cartAPI.js 또는 actions.js
export const addCart = (pid, size) => async (dispatch) => {
    dispatch(addCartItem({ "cartItem": {"pid": pid, "size": size, "qty": 1} }));
    dispatch(updateCartCount());
};