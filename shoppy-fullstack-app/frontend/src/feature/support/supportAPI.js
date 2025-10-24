import React from 'react';
import { axiosData, groupByRows, axiosGet, axiosPost } from '../../utils/dataFetch.js';

/**
    고객관리 list
*/
export const getList = async(stype) => {
    const url = "/support/list";
    const list = await axiosPost(url, {"stype": stype});
    return list;
}

/**
export const getList = async(stype) => {
    const url = "/support/list";
    const jsonData = await axiosGet(url);
    return jsonData;
}
*/