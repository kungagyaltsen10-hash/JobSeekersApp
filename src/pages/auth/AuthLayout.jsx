import { Link } from 'react-router-dom';

function AuthLayout({ title, subtitle, footerText, footerLink, footerLinkText, children }) {
  return (
    <div className="flex min-h-screen items-center justify-center bg-gradient-to-b from-brand-50 to-white px-4">
      <div className="card w-full max-w-md">
        <h1 className="text-2xl font-semibold">{title}</h1>
        <p className="mt-1 text-sm text-slate-500">{subtitle}</p>
        <div className="mt-6">{children}</div>
        <p className="mt-6 text-center text-sm text-slate-600">
          {footerText}{' '}
          <Link to={footerLink} className="font-semibold text-brand-600">
            {footerLinkText}
          </Link>
        </p>
      </div>
    </div>
  );
}

export default AuthLayout;
