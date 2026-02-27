export const mockSummary = {
  profile: {
    name: 'Alex Johnson',
    title: 'Senior Frontend Engineer',
    completion: 86,
  },
  resumeStatus: 'Uploaded • Last updated 2 days ago',
  locationPreferences: 'New York, Austin, Remote',
  analytics: [
    { label: 'Match Rate', value: '74%', hint: '+8% this week' },
    { label: 'Auto Applies', value: '53', hint: 'Across 12 companies' },
    { label: 'Interviews', value: '7', hint: '2 pending responses' },
    { label: 'Offers', value: '1', hint: 'Current pipeline active' },
  ],
};

export const mockMatches = [
  {
    id: 1,
    title: 'Frontend Engineer',
    company: 'NovaTech',
    city: 'Austin',
    state: 'TX',
    matchScore: 92,
    locationCompatibility: 88,
    confidence: 'High',
    applyStatus: 'Ready',
    weightedScore: { skills: 95, experience: 90, location: 88, salary: 80 },
  },
  {
    id: 2,
    title: 'React Developer',
    company: 'CloudShift',
    city: 'Seattle',
    state: 'WA',
    matchScore: 84,
    locationCompatibility: 79,
    confidence: 'Medium',
    applyStatus: 'Applied',
    weightedScore: { skills: 87, experience: 85, location: 79, salary: 82 },
  },
  {
    id: 3,
    title: 'UI Platform Engineer',
    company: 'ScaleBox',
    city: 'New York',
    state: 'NY',
    matchScore: 76,
    locationCompatibility: 71,
    confidence: 'Low',
    applyStatus: 'Ready',
    weightedScore: { skills: 80, experience: 78, location: 71, salary: 74 },
  },
];

export const mockApplications = [
  { id: 1, role: 'Frontend Engineer', company: 'NovaTech', city: 'Austin', status: 'Interviewing' },
  { id: 2, role: 'React Developer', company: 'CloudShift', city: 'Seattle', status: 'Applied' },
  { id: 3, role: 'UI Engineer', company: 'PixelForge', city: 'New York', status: 'Offer' },
  { id: 4, role: 'Software Engineer', company: 'BrightAI', city: 'Boston', status: 'Rejected' },
];
