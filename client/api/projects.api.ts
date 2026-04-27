import { createCrudApi } from "@/api/core/crud.api";
import type { ProjectRequest } from "@/types/request";
import type { ProjectResponse } from "@/types/response";

export const projectsApi = createCrudApi<ProjectRequest, ProjectResponse>("/projects");
