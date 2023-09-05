import LoginForm from '@/components/auth/LoginForm';
import withNoAuth from '@/hooks/withNoAuth';
import React from 'react';

function Login() {
  return (
    <>
      <LoginForm />
    </>
  );
}

// login한 유저는 login page 접근 불가
// Login.Layout = withNoAuth();
export default withNoAuth(Login);
