const LABEL_MAP = {
  skills: 'Skills',
  experience: 'Experience',
  location: 'Location',
  salary: 'Salary',
};

function ScoreBreakdown({ score }) {
  const entries = Object.entries(score || {});
  return (
    <div className="space-y-2">
      {entries.map(([key, value]) => (
        <div key={key} className="space-y-1">
          <div className="flex justify-between text-xs text-slate-500">
            <span>{LABEL_MAP[key] || key}</span>
            <span>{value}%</span>
          </div>
          <div className="h-2 rounded-full bg-slate-100">
            <div className="h-2 rounded-full bg-brand-600" style={{ width: `${value}%` }} />
          </div>
        </div>
      ))}
    </div>
  );
}

export default ScoreBreakdown;
