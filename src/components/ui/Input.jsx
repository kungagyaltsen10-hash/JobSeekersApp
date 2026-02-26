function Input({ label, error, ...props }) {
  return (
    <label className="flex w-full flex-col gap-2">
      {label && <span className="text-sm font-medium text-slate-700">{label}</span>}
      <input
        className="w-full rounded-xl border border-slate-300 px-3 py-2 outline-none transition focus:border-brand-500 focus:ring-2 focus:ring-brand-50"
        {...props}
      />
      {error && <span className="text-sm text-rose-600">{error}</span>}
    </label>
  );
}

export default Input;
