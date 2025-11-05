import React from 'react';
import { TbThumbUp } from 'react-icons/tb';

export function LikeItem({style, icons, value}) {
    return (
        <button type='button' className={style}>
            <span>{ icons === "tb" && <TbThumbUp />}</span><span>0</span>
        </button>
    );
}

