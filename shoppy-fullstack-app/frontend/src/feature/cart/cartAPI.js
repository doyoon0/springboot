import React from 'react';
import { addCartItem, updateCartCount, showCartItem, updateTotalPrice, updateCartItem, removeCartItem } from './cartSlice.js';
import { axiosData, axiosPost } from '../../utils/dataFetch.js';

export const removeCart = (cid) => async (dispatch) => {
    const url = "/cart/deleteItem";
    const data = {"cid" : cid};
    const rows = await axiosPost(url, data);

    const { userId } = JSON.parse(localStorage.getItem("loginInfo"));

    dispatch(showCart());
    dispatch(getCartCount(userId));
//    dispatch(removeCartItem({"cid": cid}));
//    dispatch(updateTotalPrice());
//    dispatch(updateCartCount());
}

export const showCart = () => async (dispatch) => {
    const url = "/cart/list";
    const { userId } = JSON.parse(localStorage.getItem("loginInfo"));
    const jsonData = await axiosPost(url, {"id": userId});
    console.log("으악 : ", jsonData);

    dispatch(showCartItem({ "items": jsonData }));
    jsonData.length && dispatch(updateTotalPrice({"totalPrice" : jsonData[0].totalPrice}));
}

export const updateCart = (cid, type) => async (dispatch) => {
    const url = "/cart/updateQty";
    const data = {"cid": cid, "type": type};
    const rows = await axiosPost(url, data);
    const { userId } = JSON.parse(localStorage.getItem("loginInfo"));
    dispatch(getCartCount(userId));
    dispatch(showCart());

//    dispatch(updateCartCount({"count": 1, "type": type}));
//    dispatch(updateCartItem({ "cid": cid, "type": type })); //slice에서 item으로 구조분해할당
    return rows;
//    dispatch(updateTotalPrice());

}

export const checkQty = async(pid, size, id) => { //상품번호랑 사이즈가 같으면 같은상품인것
    //쇼핑백 추가한 상품과 사이즈가 장바구니 테이블에 있는지 유무 확인
    const url = "/cart/checkQty";
    const data = {"pid": pid, "size": size, "id": id};
    const jsonData = await axiosPost(url, data);
    return jsonData;
}

// cartAPI.js 또는 actions.js
export const addCart = (pid, size) => async (dispatch) => {
    const { userId } = JSON.parse(localStorage.getItem("loginInfo"));
    const checkResult = await checkQty(pid, size, userId);

    //db에 반영
    if(!checkResult.checkQty) {
        const url = "/cart/add";
        const item = {"pid": pid, "size": size, "qty": 1, "id": userId};
        const rows = await axiosPost(url, item);
        alert("새로운 상품이 추가되었습니다!!!");
        dispatch(updateCartCount({"count": 1, "type": true})); //아이콘에 +1 증가
    } else {
        dispatch(updateCart(checkResult.cid, "+"));
        dispatch(updateCartCount({"count": 1, "type": true}));
        alert("새로운 상품이 추가되었습니다.");
    }

    //cartCount에 넣어서 화면에 노출
    dispatch(getCartCount(userId));

    return 1;
};

/** 회원아이디별 장바구니 카운트 */
export const getCartCount = (id) => async (dispatch) => {
    const url = "/cart/count";
    const data = {"id": id};
    const jsonData = await axiosPost(url, data);
    dispatch(updateCartCount({"count": jsonData.sumQty})) //db에서 가지고온 값이 그대로 들아가야함. (+/- 로직에서 변경된 것)
}