import React from "react";
import {Typography} from "@mui/material";
import {User} from "../../@types/User";

interface UserInfoProps {
    user: User | null;
}

const UserInfo: React.FC<UserInfoProps> = ({user}) => (
    <>
        <Typography variant="h6" gutterBottom>사용자 정보</Typography>
        <Typography>닉네임: {user?.nickName}</Typography>
        <Typography>이름: {user?.userName}</Typography>
    </>
);

export default UserInfo;
