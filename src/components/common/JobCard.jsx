import Badge from '../ui/Badge';
import Card from '../ui/Card';
import ScoreBreakdown from './ScoreBreakdown';
import ConfidenceIndicator from './ConfidenceIndicator';

function JobCard({ job }) {
  return (
    <Card className="space-y-4">
      <div className="flex flex-wrap items-center justify-between gap-2">
        <div>
          <h3 className="text-lg font-semibold">{job.title}</h3>
          <p className="text-sm text-slate-500">
            {job.company} • {job.city}, {job.state}
          </p>
        </div>
        <Badge tone="success">{job.matchScore}% match</Badge>
      </div>

      <div className="flex flex-wrap items-center gap-2">
        <ConfidenceIndicator confidence={job.confidence} />
        <Badge tone="info">Location fit: {job.locationCompatibility}%</Badge>
        <Badge tone={job.applyStatus === 'Applied' ? 'success' : 'warning'}>{job.applyStatus}</Badge>
      </div>

      <ScoreBreakdown score={job.weightedScore} />
    </Card>
  );
}

export default JobCard;
