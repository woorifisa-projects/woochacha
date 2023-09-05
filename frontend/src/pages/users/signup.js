import SignupForm from '@/components/auth/SignupForm';
import withNoAuth from '@/hooks/withNoAuth';

function Signup() {
  return (
    <>
      <SignupForm />
    </>
  );
}

// login한 유저는 Signup page 접근 불가
// Signup.Layout = withNoAuth();
export default withNoAuth(Signup);
