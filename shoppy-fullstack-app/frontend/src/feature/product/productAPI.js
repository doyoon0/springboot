import React from 'react';
import { createProduct, filterProduct } from './productSlice.js';
import { axiosData, groupByRows, axiosGet, axiosPost } from '../../utils/dataFetch.js';

/**
    상품 상세 정보
*/
export const getDetailinfo = async(pid) => {
    const url = "/product/detailinfo";
    const info = await axiosPost(url, {"pid":pid});
    const list = JSON.parse(info.list);
    return {...info, list: list};
//    dispatch(filterProduct({ "product": product })); slice로 넘어가는건 아니니까
}

export const getProduct = (pid) => async (dispatch) => {
    // dispatch(filterProduct(pid)); 파라미터가 하나라면.
    const url = "/product/pid";
    const product = await axiosPost(url, {"pid":pid});
    console.log("product------->", product);
    dispatch(filterProduct({ "product": product }));
}

export const getProductList = (number) => async (dispatch) => {
    //slice에 있는 productList를 뭐 할필요 없고 값만 넘겨주면 되니까 여기서 jsonData 호출
//    const jsonData = await axiosData("/data/products.json");
    const url = "/product/all";
    const jsonData = await axiosGet(url);
    const rows = groupByRows(jsonData, number);
    dispatch(createProduct({ "productList": rows, "products": jsonData }));
}
