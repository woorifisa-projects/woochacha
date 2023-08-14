import { RecoilRoot } from 'recoil';
// import { QueryClient, QueryClientProvider } from 'react-query';
import '@/styles/globals.css';

function App({ Component, pageProps }) {
  return (
    <RecoilRoot>
      <Component {...pageProps} />
    </RecoilRoot>
  );
}

export default App;
