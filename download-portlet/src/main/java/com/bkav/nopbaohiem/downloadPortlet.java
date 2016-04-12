package com.bkav.nopbaohiem;

import java.io.IOException;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.header-portlet-css=/css/download.css",
		"com.liferay.portlet.header-portlet-javascript=/js/jquery-1.12.3.js",
		"com.liferay.portlet.footer-portlet-javascript=/js/download.js",
		"javax.portlet.display-name=download-portlet Portlet",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language"
	},
	service = Portlet.class
)
public class downloadPortlet extends MVCPortlet {
	
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		try {
			ThemeDisplay themeDisplay= (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			long groupId = themeDisplay.getLayout().getGroupId();
			Long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			DLFolder folder = DLFolderLocalServiceUtil.getFolder(groupId,parentFolderId,"Installation Files");
			if (folder != null) {
				List<DLFileEntry> fileList = DLFileEntryLocalServiceUtil.getFileEntries(groupId, folder.getFolderId());
				for (DLFileEntry file : fileList) {
					System.out.println(file.getFileName());
				}
			} else {
				System.out.println("Folder does not exist");
			}
		} catch (Exception ex) {
			_log.error(ex);
		}
	}
	
	private static final Log _log = LogFactoryUtil.getLog(downloadPortlet.class);
}