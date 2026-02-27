import { useEffect, useMemo, useState } from 'react';
import Card from '../../components/ui/Card';
import JobCard from '../../components/common/JobCard';
import { jobService } from '../../services/jobService';
import { mockMatches } from './mockData';

function JobMatchesPage() {
  const [matches, setMatches] = useState(mockMatches);
  const [sortBy, setSortBy] = useState('desc');

  useEffect(() => {
    const fetchMatches = async () => {
      try {
        const data = await jobService.getMatches();
        setMatches(data.jobs || data);
      } catch {
        // fallback
      }
    };
    fetchMatches();
  }, []);

  const sortedMatches = useMemo(() => {
    return [...matches].sort((a, b) => (sortBy === 'desc' ? b.matchScore - a.matchScore : a.matchScore - b.matchScore));
  }, [matches, sortBy]);

  return (
    <div className="space-y-4">
      <Card title="Job Matches" subtitle="Curated opportunities ranked by AI match quality.">
        <div className="flex items-center justify-end">
          <select
            className="rounded-xl border border-slate-300 px-3 py-2 text-sm"
            value={sortBy}
            onChange={(e) => setSortBy(e.target.value)}
          >
            <option value="desc">Sort by score: High to Low</option>
            <option value="asc">Sort by score: Low to High</option>
          </select>
        </div>
      </Card>

      <div className="space-y-4">
        {sortedMatches.map((job) => (
          <JobCard key={job.id} job={job} />
        ))}
      </div>
    </div>
  );
}

export default JobMatchesPage;
