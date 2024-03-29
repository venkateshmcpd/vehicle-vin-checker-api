/****** Object:  Table [dbo].[api_request_error_log]    Script Date: 3/4/2024 11:33:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[api_request_error_log](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[error_type_id] [int] NOT NULL,
	[vehicle_info_id] [int] NULL,
	[requested_vin] [nvarchar](50) NULL,
	[requested_make] [nvarchar](250) NULL,
	[requested_model] [nvarchar](250) NULL,
	[requested_model_year] [nvarchar](50) NULL,
	[error_code] [nvarchar](100) NULL,
	[error_text] [nvarchar](500) NULL,
	[created_on] [datetime] NOT NULL,
	[updated_on] [datetime] NULL,
	[created_by] [int] NULL,
	[updated_by] [int] NULL,
	[is_deleted] [bit] NOT NULL,
 CONSTRAINT [PK_api_request_log] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[error_type]    Script Date: 3/4/2024 11:33:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[error_type](
	[id] [int] NOT NULL,
	[name] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_error_type] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[make]    Script Date: 3/4/2024 11:33:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[make](
	[id] [int] NOT NULL,
	[name] [varchar](250) NOT NULL,
	[manufacturer_id] [int] NULL,
	[created_on] [datetime] NULL,
	[updated_on] [datetime] NULL,
	[created_by] [int] NULL,
	[updated_by] [int] NULL,
	[is_deleted] [bit] NOT NULL,
 CONSTRAINT [PK_Make] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [U_MakeName] UNIQUE NONCLUSTERED 
(
	[name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[manufacturer]    Script Date: 3/4/2024 11:33:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[manufacturer](
	[id] [int] NOT NULL,
	[name] [varchar](250) NOT NULL,
	[created_on] [datetime] NOT NULL,
	[updated_on] [datetime] NULL,
	[created_by] [int] NULL,
	[updated_by] [int] NULL,
	[is_deleted] [bit] NOT NULL,
 CONSTRAINT [PK_Manufacturer] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[model]    Script Date: 3/4/2024 11:33:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[model](
	[id] [int] NOT NULL,
	[name] [varchar](250) NOT NULL,
	[make_id] [int] NULL,
	[created_on] [datetime] NOT NULL,
	[updated_on] [datetime] NULL,
	[created_by] [int] NULL,
	[updated_by] [int] NULL,
	[is_deleted] [bit] NOT NULL,
 CONSTRAINT [PK_Model] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[vehicle_info]    Script Date: 3/4/2024 11:33:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[vehicle_info](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[vin] [nvarchar](50) NOT NULL,
	[manufacturer_id] [int] NULL,
	[model_year] [int] NULL,
	[created_on] [datetime] NOT NULL,
	[updated_on] [datetime] NULL,
	[created_by] [int] NULL,
	[updated_by] [int] NULL,
	[is_deleeted] [bit] NOT NULL,
 CONSTRAINT [PK_vehicle_vin] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER TABLE [dbo].[api_request_error_log] ADD  CONSTRAINT [DF_api_request_log_inserted_on]  DEFAULT (getdate()) FOR [created_on]
GO
ALTER TABLE [dbo].[api_request_error_log] ADD  CONSTRAINT [DF_api_request_error_log_is_deleted]  DEFAULT ((0)) FOR [is_deleted]
GO
ALTER TABLE [dbo].[make] ADD  CONSTRAINT [DF_Make_CreatedOn]  DEFAULT (getdate()) FOR [created_on]
GO
ALTER TABLE [dbo].[make] ADD  CONSTRAINT [DF_Make_is_deleted]  DEFAULT ((0)) FOR [is_deleted]
GO
ALTER TABLE [dbo].[manufacturer] ADD  CONSTRAINT [DF_Manufacturer_created_on]  DEFAULT (getdate()) FOR [created_on]
GO
ALTER TABLE [dbo].[manufacturer] ADD  CONSTRAINT [DF_Manufacturer_is_deleted]  DEFAULT ((0)) FOR [is_deleted]
GO
ALTER TABLE [dbo].[model] ADD  CONSTRAINT [DF_Model_CreatedOn]  DEFAULT (getdate()) FOR [created_on]
GO
ALTER TABLE [dbo].[model] ADD  CONSTRAINT [DF_model_is_deleted]  DEFAULT ((0)) FOR [is_deleted]
GO
ALTER TABLE [dbo].[vehicle_info] ADD  CONSTRAINT [DF_vehicle_info_created_on]  DEFAULT (getdate()) FOR [created_on]
GO
ALTER TABLE [dbo].[vehicle_info] ADD  CONSTRAINT [DF_vehicle_info_is_deleeted]  DEFAULT ((0)) FOR [is_deleeted]
GO
ALTER TABLE [dbo].[api_request_error_log]  WITH CHECK ADD  CONSTRAINT [FK_api_request_error_log_api_request_error_log] FOREIGN KEY([error_type_id])
REFERENCES [dbo].[error_type] ([id])
GO
ALTER TABLE [dbo].[api_request_error_log] CHECK CONSTRAINT [FK_api_request_error_log_api_request_error_log]
GO
ALTER TABLE [dbo].[api_request_error_log]  WITH CHECK ADD  CONSTRAINT [FK_api_request_error_log_vehicle_info] FOREIGN KEY([vehicle_info_id])
REFERENCES [dbo].[vehicle_info] ([id])
GO
ALTER TABLE [dbo].[api_request_error_log] CHECK CONSTRAINT [FK_api_request_error_log_vehicle_info]
GO
ALTER TABLE [dbo].[make]  WITH CHECK ADD  CONSTRAINT [FK_make_manufacturer] FOREIGN KEY([manufacturer_id])
REFERENCES [dbo].[manufacturer] ([id])
GO
ALTER TABLE [dbo].[make] CHECK CONSTRAINT [FK_make_manufacturer]
GO
ALTER TABLE [dbo].[model]  WITH CHECK ADD  CONSTRAINT [FK_model_make] FOREIGN KEY([make_id])
REFERENCES [dbo].[make] ([id])
GO
ALTER TABLE [dbo].[model] CHECK CONSTRAINT [FK_model_make]
GO
ALTER TABLE [dbo].[vehicle_info]  WITH CHECK ADD  CONSTRAINT [FK_vehicle_info_Manufacturer] FOREIGN KEY([manufacturer_id])
REFERENCES [dbo].[manufacturer] ([id])
GO
ALTER TABLE [dbo].[vehicle_info] CHECK CONSTRAINT [FK_vehicle_info_Manufacturer]
GO
