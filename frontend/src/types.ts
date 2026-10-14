export type Status = 'OPEN' | 'IN_PROGRESS' | 'RESOLVED';
export type Priority = 'LOW' | 'MEDIUM' | 'HIGH';
export interface Issue { id: number; title: string; description: string; assignee: string; priority: Priority; status: Status; createdAt: string; updatedAt: string; }
export type IssueInput = Pick<Issue, 'title' | 'description' | 'assignee' | 'priority' | 'status'>;
