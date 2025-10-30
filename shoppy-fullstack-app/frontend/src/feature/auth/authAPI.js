import { login, logout} from '../../feature/auth/authSlice.js';
import { validateFormCheck, validateSignupFormCheck } from '../../utils/validate.js';
import { axiosPost } from '../../utils/dataFetch.js';
import { getCartCount } from '../../feature/cart/cartAPI.js';
import { updateCartCount, resetCartCount } from '../../feature/cart/cartSlice.js';

/**
    Id 중복 체크
*/
export const getIdCheck = (id) => async(dispatch) => {
    const url = "/member/idcheck";
    const data = { "id": id }; //axiosPost에서 json으로 보내기로 약속
    const result = await axiosPost(url, data);
    return result;
}

/**
 Signup
*/
export const getSignup = (formData, param) => async(dispatch) => {
    let result = null;
    if(validateSignupFormCheck(param)) {
        const url = "/member/signup";
        result = await axiosPost(url, formData);
    }
    return result;
}

/**
 Login
*/
export const getLogin = (formData, param) => async(dispatch) => {

    if(validateFormCheck(param)) {
        /**
            SpringBoot - @RestController, @PostMapping("/member/login")
            axios api
        */
        const url = "/member/login"; //프록시를 통해 전송시 상대경로입력!!
        const result = await axiosPost(url, formData);
        console.log("result :: ", result);

        if(result.login) {
            dispatch(login({"userId": formData.id}));
            //장바구니 카운트 함수 호출
            dispatch(getCartCount(formData.id));
            return true;
        }
    }
    return false;
}

export const getLogout = () => async(dispatch) => {
    const url = "/member/logout"; //세션 무효화 작업 시작!
    const result = await axiosPost(url, {}); //JSON 보낼거 없으므로 빈 객체로 보냄. 아무것도 안보내면 mapping이 안되어서 에러남

    if(result) {
        dispatch(logout());
        dispatch(resetCartCount());
    }
    return result;
}