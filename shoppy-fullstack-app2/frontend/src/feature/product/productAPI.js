import React from 'react';
import { createProduct, filterProduct } from './productSlice.js';
import { axiosData, groupByRows } from '../../utils/dataFetch.js';

export const getProduct = (pid) => async (dispatch) => {
    // dispatch(filterProduct(pid)); 파라미터가 하나라면.
    dispatch(filterProduct({ "pid": pid }));
}

export const getProductList = (number) => async (dispatch) => {
    //slice에 있는 productList를 뭐 할필요 없고 값만 넘겨주면 되니까 여기서 jsonData 호출
    const jsonData = await axiosData("/data/products.json");
    const rows = groupByRows(jsonData, number);
    dispatch(createProduct({ "productList": rows, "products": jsonData }));
}
