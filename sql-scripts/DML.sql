DELETE FROM [dbo].[api_request_error_log]
GO
DELETE FROM [dbo].[error_type]
GO
DELETE FROM [dbo].[vehicle_info]
GO
DELETE FROM [dbo].[model]
GO
DELETE FROM [dbo].[make]
GO
DELETE FROM [dbo].[manufacturer]
GO
INSERT [dbo].[manufacturer] ([id], [name], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (1042, N'MERCEDES-BENZ CARS', CAST(N'2024-03-04T21:07:03.227' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[manufacturer] ([id], [name], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (14805, N'FERRARI S.P.A.', CAST(N'2024-03-04T21:12:05.850' AS DateTime), NULL, NULL, NULL, 0)
GO
INSERT [dbo].[make] ([id], [name], [manufacturer_id], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (449, N'MERCEDES-BENZ', 1042, CAST(N'2024-03-04T21:07:03.230' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[make] ([id], [name], [manufacturer_id], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (603, N'FERRARI', 14805, CAST(N'2024-03-04T21:12:05.857' AS DateTime), NULL, NULL, NULL, 0)
GO
INSERT [dbo].[model] ([id], [name], [make_id], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (3606, N'430 Scuderia', 603, CAST(N'2024-03-04T21:12:05.860' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[model] ([id], [name], [make_id], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (14015, N'500', 449, CAST(N'2024-03-04T21:07:03.233' AS DateTime), NULL, NULL, NULL, 0)
GO
SET IDENTITY_INSERT [dbo].[vehicle_info] ON 

INSERT [dbo].[vehicle_info] ([id], [vin], [manufacturer_id], [model_year], [created_on], [updated_on], [created_by], [updated_by], [is_deleeted]) VALUES (5, N'WDBEA36E0NB682816', 1042, 1992, CAST(N'2024-03-04T21:07:03.257' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[vehicle_info] ([id], [vin], [manufacturer_id], [model_year], [created_on], [updated_on], [created_by], [updated_by], [is_deleeted]) VALUES (6, N'ZFFKW64A080466277', 14805, 2008, CAST(N'2024-03-04T21:12:05.863' AS DateTime), NULL, NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[vehicle_info] OFF
GO
INSERT [dbo].[error_type] ([id], [name]) VALUES (1, N'Requested Data Mismatch')
INSERT [dbo].[error_type] ([id], [name]) VALUES (2, N'VIN Not Found')
GO
SET IDENTITY_INSERT [dbo].[api_request_error_log] ON 

INSERT [dbo].[api_request_error_log] ([id], [error_type_id], [vehicle_info_id], [requested_vin], [requested_make], [requested_model], [requested_model_year], [error_code], [error_text], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (2, 1, NULL, N'WUAS6!F!1EA900006', N'MERCEDES-BENZ1', N'500', N'1992', N'1,5,14,400', N'1 - Check Digit (9th position) does not calculate properly; 5 - VIN has errors in few positions; 14 - Unable to provide information for some of the characters in the VIN, based on the manufacturer submission.; 400 - Invalid Characters Present', CAST(N'2024-03-04T21:07:23.490' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[api_request_error_log] ([id], [error_type_id], [vehicle_info_id], [requested_vin], [requested_make], [requested_model], [requested_model_year], [error_code], [error_text], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (3, 2, 5, N'WDBEA36E0NB682816', N'MERCEDES-BENZ1', N'500', N'1992', NULL, NULL, CAST(N'2024-03-04T21:11:07.223' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[api_request_error_log] ([id], [error_type_id], [vehicle_info_id], [requested_vin], [requested_make], [requested_model], [requested_model_year], [error_code], [error_text], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (4, 2, 6, N'ZFFKW64A080466277', N'MERCEDES-BENZ1', N'500', N'1992', N'0', N'0 - VIN decoded clean. Check Digit (9th position) is correct', CAST(N'2024-03-04T21:12:05.893' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[api_request_error_log] ([id], [error_type_id], [vehicle_info_id], [requested_vin], [requested_make], [requested_model], [requested_model_year], [error_code], [error_text], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (5, 2, 5, N'WDBEA36E0NB682816', N'MERCEDES-BENZ1', N'500', N'1992', NULL, NULL, CAST(N'2024-03-04T21:34:00.290' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[api_request_error_log] ([id], [error_type_id], [vehicle_info_id], [requested_vin], [requested_make], [requested_model], [requested_model_year], [error_code], [error_text], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (6, 2, 5, N'WDBEA36E0NB682816', N'MERCEDES-BENZ1', N'500', N'1992', NULL, NULL, CAST(N'2024-03-04T21:34:19.180' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[api_request_error_log] ([id], [error_type_id], [vehicle_info_id], [requested_vin], [requested_make], [requested_model], [requested_model_year], [error_code], [error_text], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (7, 2, 5, N'WDBEA36E0NB682816', N'MERCEDES-BENZ1', N'500', N'1992', NULL, NULL, CAST(N'2024-03-04T21:35:12.897' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[api_request_error_log] ([id], [error_type_id], [vehicle_info_id], [requested_vin], [requested_make], [requested_model], [requested_model_year], [error_code], [error_text], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (8, 2, 6, N'ZFFKW64A080466277', N'MERCEDES-BENZ1', N'500', N'1992', NULL, NULL, CAST(N'2024-03-04T22:06:32.737' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[api_request_error_log] ([id], [error_type_id], [vehicle_info_id], [requested_vin], [requested_make], [requested_model], [requested_model_year], [error_code], [error_text], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (9, 2, 6, N'ZFFKW64A080466277', N'MERCEDES-BENZ1', N'500', N'1992', NULL, NULL, CAST(N'2024-03-04T22:07:41.103' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[api_request_error_log] ([id], [error_type_id], [vehicle_info_id], [requested_vin], [requested_make], [requested_model], [requested_model_year], [error_code], [error_text], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (10, 2, 6, N'ZFFKW64A080466277', N'MERCEDES-BENZ1', N'500', N'1992', NULL, NULL, CAST(N'2024-03-04T22:09:20.470' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[api_request_error_log] ([id], [error_type_id], [vehicle_info_id], [requested_vin], [requested_make], [requested_model], [requested_model_year], [error_code], [error_text], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (11, 2, 6, N'ZFFKW64A080466277', N'MERCEDES-BENZ1', N'500', N'1992', NULL, NULL, CAST(N'2024-03-04T22:11:05.897' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[api_request_error_log] ([id], [error_type_id], [vehicle_info_id], [requested_vin], [requested_make], [requested_model], [requested_model_year], [error_code], [error_text], [created_on], [updated_on], [created_by], [updated_by], [is_deleted]) VALUES (12, 2, 6, N'ZFFKW64A080466277', N'MERCEDES-BENZ1', N'500', N'1992', NULL, NULL, CAST(N'2024-03-04T23:27:22.403' AS DateTime), NULL, NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[api_request_error_log] OFF
GO
