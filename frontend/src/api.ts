import type { Issue, IssueInput } from './types';
async function request<T>(path: string, init?: RequestInit): Promise<T> {
  let response: Response;
  try { response = await fetch(`/api${path}`, { ...init, headers: { 'Content-Type': 'application/json', ...init?.headers } }); }
  catch { throw new Error('Cannot connect to the API. Is the Spring Boot backend running on port 8080?'); }
  if (!response.ok) {
    const body = await response.json().catch(() => ({}));
    throw new Error(Array.isArray(body.details) ? body.details.join('; ') : body.error || `Request failed (${response.status})`);
  }
  if (response.status === 204) return undefined as T;
  return response.json() as Promise<T>;
}
export const api = {
  list: () => request<Issue[]>('/issues'),
  create: (data: IssueInput) => request<Issue>('/issues', { method: 'POST', body: JSON.stringify(data) }),
  update: (id: number, data: IssueInput) => request<Issue>(`/issues/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  remove: (id: number) => request<void>(`/issues/${id}`, { method: 'DELETE' })
};
